import numpy as np
from delta import delta1, delta2

class KF:

    def __init__(self):


        self.dt = 1
        x0 = 0  #hopefully
        v0 = 0  #it should eventually fix itself

        self.estimate = np.array([[x0], [v0]]) # x
        self.stateCovariance = np.array( [[0.5, 1e-9], [1e-9, 0.5]] ) # p

        self.stateCovarianceNoise = np.array( [[0.1, 0], [0, 0.1]] ) # q
        self.estimatePredictNoise = np.array( [[0], [0]] ) # w
        self.dataNoise = np.array( [[0], [0]] ) # z

        self.dataError = np.array( [[0.5, 1e-9], [ 1e-9, 1]] ) # r

        # If self.dt is varying this needs to move the the beginning of the for loop
        self.convertA = np.array( [[1, self.dt],[0, 1]] ) # A
        self.convertB = np.array( [[self.dt**2/2],[self.dt]] ) # B
        self.convertC = np.array( [[1, 0], [0, 1]] ) # C
        self.convertH = np.array( [[1, 0], [0,1]] ) # H


    def predict(self, past_points):
        acceleration = np.array([[delta2(self.dt, past_points)]])

        estimatePredict = np.dot( self.convertA, self.estimate ) + np.dot( self.convertB, acceleration ) + self.estimatePredictNoise

        stateConvariancePredict = np.dot( self.convertA, np.dot( self.stateCovariance, self.convertA.T )) + self.stateCovarianceNoise
        stateConvariancePredict[0][1] = 0
        stateConvariancePredict[1][0] = 0
       
        x_cur = past_points[-1]
        v_cur = delta1(self.dt, past_points)
        cur_state = np.array([[x_cur], [v_cur]])
        d = np.dot( self.convertC, cur_state ) + self.dataNoise
        
        print()
        print(d)

        temp = np.dot(stateConvariancePredict, self.convertH.T)
        gain = temp/( np.dot( self.convertH, temp) + self.dataError )
       
        self.estimate = estimatePredict + np.dot( gain, d - np.dot( self.convertH, estimatePredict ) )

        stateCovariance = np.dot(( 1 - np.dot( gain, self.convertH)), stateConvariancePredict )

        # Do prediction stuff
        futurePoints = []
        for i in range(20):
            estA = np.array( [[1, i*self.dt],[0, 1]] ) # A
            estB = np.array( [[(i*self.dt)**2/2],[i*self.dt]] ) # B
            futurePoints.append( np.dot( estA, self.estimate ) + np.dot( estB, acceleration ) )
        print(futurePoints[-1])    


        return futurePoints