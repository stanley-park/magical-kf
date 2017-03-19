import numpy as np

def stencil5(ys, coeffs):
    return np.multiply(np.array(ys), np.array(coeffs))

def delta1(h, ys):
    coeffs = [1, -8, 0, 8, -1]
    return sum(stencil5(ys, coeffs)) / (12*h)

def delta2(h, ys):
    coeffs = [-1, 16, -30, 16, -1]
    return sum(stencil5(ys, coeffs)) / (12*h**2)

def delta3(h, ys):
    coeffs = [-1, 2, 0, -2, 1]
    return sum(stencil5(ys, coeffs)) / (2*h**3)

def delta4(h, ys):
    coeffs = [1, -4, 6, -4, 1]
    return sum(stencil5(ys, coeffs)) / (h**4)