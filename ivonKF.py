import numpy as np
from delta import *

class KF:

    def __init__(self):


        self.dt = 1
        x0 = 0  #hopefully
        v0 = 0  #it should eventually fix itself
        a0 = 0

        self.estimate = np.array([[x0], [v0], [a0]]) # x
        self.stateCovariance = np.array( [[0.5, 1e-9, 1e-9], [1e-9, 0.5, 1e-9], [1e-9, 1e-9, 0.5]] ) # p

        self.stateCovarianceNoise = np.array( [[0.1, 0, 0], [0, 0.1, 0], [0, 0, 0.1]] ) # q
        self.estimatePredictNoise = np.array( [[0], [0], [0]] ) # w
        self.dataNoise = np.array( [[0], [0], [0]] ) # z

        self.dataError = np.array( [[0.5, 1e-9, 1e-9], [ 1e-9, 1, 1e-9], [1e-9, 1e-9, 1.5]] ) # r

        # If self.dt is varying this needs to move the the beginning of the for loop
        self.convertA = np.array( [[1, self.dt, self.dt**2/2],[0, 1, self.dt], [0, 0, 1]] ) # A
        self.convertB = np.array( [[self.dt**3/6],[self.dt**2/2], [self.dt]] ) # B
        self.convertC = np.array( [[1, 0, 0], [0, 1, 0], [0, 0, 1]] ) # C
        self.convertH = np.array( [[1, 0, 0], [0,1,0], [0, 0, 1]] ) # H


    def predict(self, past_points):
        jerk = np.array([[delta3(self.dt, past_points)]])

        estimatePredict = np.dot( self.convertA, self.estimate ) + np.dot( self.convertB, jerk ) + self.estimatePredictNoise

        temp2 = np.dot( self.convertA, np.dot( self.stateCovariance, self.convertA.T )) + self.stateCovarianceNoise
        stateConvariancePredict = [[0, 0, 0] for i in range(3)]
        stateConvariancePredict[0][0] = temp2[0][0]
        stateConvariancePredict[1][1] = temp2[1][1]
        stateConvariancePredict[2][2] = temp2[2][2]
       
        x_cur = past_points[-1]
        v_cur = delta1(self.dt, past_points)
        a_cur = delta2(self.dt, past_points)
        cur_state = np.array([[x_cur], [v_cur], [a_cur]])
        d = np.dot( self.convertC, cur_state ) + self.dataNoise
        
        print()
        print(d)

        temp = np.dot(stateConvariancePredict, self.convertH.T)
        gain = temp/( np.dot( self.convertH, temp) + self.dataError )
       
        self.estimate = estimatePredict + np.dot( gain, d - np.dot( self.convertH, estimatePredict ) )

        stateCovariance = np.dot(( 1 - np.dot( gain, self.convertH)), stateConvariancePredict )

        # Do prediction stuff
        futurePoints = []
        for i in range(100):
            estA = np.array( [[1, i*self.dt, (i*self.dt)**2/2],[0, 1, i*self.dt], [0,0,1]] ) # A
            estB = np.array( [[(i*self.dt)**3/6], [(i*self.dt)**2/2],[i*self.dt]] ) # B
            futurePoints.append( np.dot( estA, self.estimate ) + np.dot( estB, jerk ) )
        print(futurePoints[-1])    


        return futurePoints

        