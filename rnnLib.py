import numpy as np

class rnn(object):
    def __init__(self):
        self.inputSize = 1
        self.hiddenSize = 64
        self.outputSize = 1
        self.learningRate = 1e-1
        
        #model parameters
        self.wih = np.random.rand(self.hiddenSize, self.inputSize)*0.01
        self.whh = np.random.rand(self.hiddenSize, self.hiddenSize)*0.01
        self.who = np.random.rand(self.outputSize, self.hiddenSize)*0.01
        self.bh = np.zeros((self.hiddenSize, 1))
        self.by = np.zeros((self.outputSize, 1))

        #memory for adagrad update
        self.mwih = np.zeros_like(self.wih)
        self.mwhh = np.zeros_like(self.whh)
        self.mwho = np.zeros_like(self.who)
        self.mbh = np.zeros_like(self.bh)
        self.mby = np.zeros_like(self.by) 

    def long_predict(self, inputs):
        iterations = 10
        xs, hs, outputs = {}, {}, {}
        
        hprev = np.zeros((self.hiddenSize,1))
        oldLen = len(inputs)
        hs[-1] = hprev
        for index in (range(oldLen * iterations)):
            xs[index] = np.array(inputs[index])
            hs[index] = np.tanh( np.dot(self.wih, xs[index]) + np.dot(self.whh, hs[index-1]) + self.bh)
            outputs[index] = np.dot(self.who, hs[index]) + self.by
            if (index >= oldLen):
                inputs.append(outputs[-1])        
            
        return outputs[5:]
    
    def forward(self, inputs, hprev):
        xs, hs, outputs = {}, {}, {}
        
        hs[-1] = hprev
        for index in range(len(inputs)):
            xs[index] = np.array(inputs[index])
            print( "xs====================")
            print( xs[index].shape )
            hs[index] = np.tanh( np.dot(self.wih, xs[index]) + np.dot(self.whh, hs[index-1]) + self.bh)
            print("hs==================")
            print( hs[index].shape )
            outputs[index] = np.dot(self.who, hs[index]) + self.by 
            print( "output ===============")
            print( outputs[index].shape )
            
        return xs, hs, outputs

    def backwards(self, xs, hs, outputs, targets):
        dwih = np.zeros_like(self.wih)
        dwhh = np.zeros_like(self.whh)
        dwho = np.zeros_like(self.who)
        dbh = np.zeros_like(self.bh)
        dby = np.zeros_like(self.by)
        dhnext = np.zeros_like(hs[0])
        for index in reversed(range(len(outputs))):
            dy = targets[index] - outputs[index]
            
            dwho += np.dot(dy, hs[index].T)
            dby += dy
            
            dh = np.dot(self.who.T, dy) + dhnext
            dhraw = np.multiply(dh, (1-np.multiply(hs[index],hs[index])))
            #dhraw *= dh # usinging a divide instead of a multiplier right here
            
            dbh += dhraw
            dwih += np.dot(dhraw, xs[index].T)
            dwhh += np.dot(dhraw, hs[index-1].T)
            
            dhnext = np.dot(self.whh.T, dhraw)
        for dparam in [dwih, dwhh, dwho, dbh, dby]:
            np.clip(dparam, -5, 5, out=dparam)
            
        for param, dparam, mparam in zip([self.wih, self.whh, self.who, self.bh, self.by],
                                        [dwih, dwhh, dwho, dbh, dby],
                                        [self.mwih, self.mwhh, self.mwho, self.mbh, self.mby]):
            #print(dparam)
            mparam += dparam*dparam
            #print(- learningRate * dparam / np.sqrt( mparam + 0.0001))
            #print(param)
            param += self.learningRate * dparam / np.sqrt( mparam + 1e-8)
            #print(param)
        #return (dwih, dwhh, dwho, dbh, dby)
    
    def train(self, data):
        p = 0
        hprev = np.zeros((self.hiddenSize, 1))
        for i in range(1000):
            
            inputs = data[p:-1]
            targets = [np.matrix(d) for d in data[1:]]
            xs, hs, outputs = self.forward(inputs, hprev)
            print(outputs)
            print(targets)
                
            self.backwards(xs, hs, outputs, targets)
            #updateParam()  
            p+=1

    def predict(self, data):
        #print("test input",data[:-2])
        #print("test output", data[1:-1])

        self.train(data)
        hprev = np.zeros((self.hiddenSize,1))
        _, _, outputs = self.forward(data[:-1], hprev)
        return outputs