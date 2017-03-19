import socket
from ivonKF import *

predx = KF()
predy = KF()

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
	x = []
	for pair in pairs:
		points = pair.split(",")
		x.append(float(points[0]))
		y.append(float(points[1]))

	px = predx.predict(x)
	py = predy.predict(y)

	outputString = ""
	for x,y in zip(px,py):
		outputString = outputString + str(x) + "," + str(y) + ";" 
	outputString = outputString + "\n"

	soc.send(outputString).encode()