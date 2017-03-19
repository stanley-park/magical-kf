import socket
from rnnLib import *

predx = rnn()
predy = rnn()

soc = socket.socket()
serverName = 'localhost'
port = 11311

soc.connect((serverName, port))

print('connected')

while True:
	message = soc.recv(1024)
	message = message[2:-1]
	print( message )

	pairs = message.decode("utf-8").split(";")
	print(pairs)
	data = []
	for pair in pairs:
		points = pair.split(",")
		x.append(float(points[0]))
		y.append(float(points[1]))

	predx.train(x)
	predy.train(y)

	px = predx.long_predict(x)
	py = predy.long_predict(y)

	outputString = ""
	for pair in prediction:
		outputString = outputString + str(pair[0]) + "," + str(pair[1]) + ";" 
	outputString = outputString + "\n"

	soc.send(outputString).encode()