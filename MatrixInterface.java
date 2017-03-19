public interface MatrixInterface {
	// boolean modify means function returns the modified matrix
	// no boolean modify means function returns a new matrix

	// matrix addition 
	public Matrix m_add(Matrix m);

	public Matrix m_add(Matrix m, boolean modify);


	// matrix multiplication 
	public Matrix m_multiply(Matrix m);

	public Matrix m_multiply(Matrix m, boolean modify);


	// transpose matrix 
	public Matrix m_transpose(Matrix m);

	public Matrix m_transpose(Matrix m, boolean modify);


	// change element in matrix
	public void m_edit(double[][] a1);

}