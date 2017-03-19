import socket

predict = rnn()

soc = socket.socket()
serverName = 'localhost'
port = "11311"

soc.connect((serverName, port))

print('connected')

while True:
	message = soc.recv(1024)
	print( message )

	pairs = message.split(";")
    data = []
    for pair in pairs:
    	points = pair.split(",")
    	data.append([float(points[0]), float(points[1])])


    predict.train(data)

    prediction = predict.long_predict(data)

    outputString = ""
    for pair in prediction:
    	outputString = outputString + str(pair[0]) + "," + str(pair[1]) + ";" 
    outputString = outputString + "\n"

    soc.send(outputString).encode()