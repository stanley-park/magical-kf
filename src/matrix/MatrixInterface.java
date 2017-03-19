package matrix;

public interface MatrixInterface {
	// boolean modify means function returns the modified matrix
	// no boolean modify means function returns a new matrix

	// matrix addition 
	public Matrix add(Matrix m);

	public Matrix add(Matrix m, boolean modify);


	// matrix scalar multiplication, s is the scalar multiple to multiply
	public Matrix sMultiply(double s);

	public Matrix sMultiply(double s, boolean modify);


	// matrix element by element division (watch out for divide by 0 errors)
	public Matrix divide(Matrix m);

	public Matrix divide(Matrix m, boolean modify);


	// matrix multiplication 
	public Matrix multiply(Matrix m);

	public Matrix multiply(Matrix m, boolean modify);


	// transpose matrix 
	public Matrix transpose();

	public Matrix transpose(boolean modify);


	// change element in matrix
	public double[][] edit();

}