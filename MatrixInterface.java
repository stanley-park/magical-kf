public interface MatrixInterface {
	// boolean modify means function returns the modified matrix
	// no boolean modify means function returns a new matrix

	// matrix addition 
	public Matrix add(Matrix m);

	public Matrix add(Matrix m, boolean modify);


	// matrix multiplication 
	public Matrix multiply(Matrix m);

	public Matrix multiply(Matrix m, boolean modify);


	// transpose matrix 
	public Matrix transpose(Matrix m);

	public Matrix transpose(Matrix m, boolean modify);


	// change element in matrix
	public double[][] edit();

}