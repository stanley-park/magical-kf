public class Matrix implements MatrixInterface {

	int rows;
	int columns;
	double[][] matrix;

	// constructor to use a matrix that was created already
	public Matrix(double[][] passedInMatrix) {
		matrix = passedInMatrix;
		this.rows = passedInMatrix.length;
		this.columns = passedInMatrix[0].length;
	}

	// constructor to create a new matrix with first arg as row, second as cols
	public Matrix(int rows, int columns) {
		matrix = new double[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}

	public static void matrixChecker(Matrix toCompare) {
		if (this.rows != m.rows && this.columns != m.columns) {
			System.out.println("Can't perform the operation on these two matrices!");
		}
		return;
	}

	// --------boolean modify means function returns the modified matrix-----------
	// ----------no boolean modify means function returns a new matrix-------------

	// matrix addition 
	public static Matrix add(Matrix m) {

		matrixChecker(m);

		Matrix newMatrix = new Matrix(this.rows, m.columns);

		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.columns; j++) {
				newMatrix.matrix[i][j] = this.matrix[i][j] + m.matrix[i][j];
			}
		}

		return newMatrix;
	}

	public static Matrix add(Matrix m, boolean modify) {
		
		matrixChecker(m);

		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.columns; j++) {
				this.matrix[i][j] = this.matrix[i][j] + m.matrix[i][j];
			}
		}

		return this;
	}


	// matrix scalar multiplication
	public static Matrix sMultiply(double s) {

		Matrix newMatrix = new Matrix(this.rows, m.columns);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				newMatrix.matrix[i][j] =  s * this.matrix[i][j];
			}
		}

		return newMatrix;
	}

	public static Matrix sMultiply(double s, boolean modify) {

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.matrix[i][j] = s * this.matrix[i][j];
			}
		}

		return this;
	}


	// matrix multiplication (actual matrix multiplication)
	public static Matrix multiply(Matrix m) {

		matrixChecker(m);

		int sum = 0;

		Matrix product = new Matrix(this.rows, m.columns);

		for ( i = 0 ; i < this.rows; i++ ) {
            for ( j = 0 ; j < m.columns; j++ ) {   
               for ( k = 0 ; k < m.rows; k++ ) {
                  sum = sum + first[i][k]*second[k][j];
               }
               multiply[i][j] = sum;
               sum = 0;
            }
         }

         return product;
	}

	public static Matrix multiply(Matrix m, boolean modify) {

		matrixChecker(m);

		int sum = 0;

		for ( i = 0 ; i < this.rows; i++ ) {
            for ( j = 0 ; j < m.columns; j++ ) {   
               for ( k = 0 ; k < m.rows; k++ ) {
                  sum = sum + first[i][k]*second[k][j];
               }
               this.matrix[i][j] = sum;
               sum = 0;
            }
         }

         return this;
	}


	// matrix element by element division (watch out for divide by 0 errors)
	public Matrix divide(Matrix m) {

		matrixChecker(m);
		Matrix newMatrix = new Matrix(this.rows, m.columns);

		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.columns; j++) {
				if (m.matrix[i][j] == 0.0) {
					newMatrix.matrix[i][j] = this.matrix[i][j] / 1e-9d;
				} else {
					newMatrix.matrix[i][j] = this.matrix[i][j] / m.matrix[i][j];
				}
			}
		}

		return newMatrix;

	}

	public static Matrix divide(Matrix m, boolean modify) {

		matrixChecker(m);

		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.columns; j++) {
				if (m.matrix[i][j] == 0.0) {
					this.matrix[i][j] = this.matrix[i][j] / 1e-9d;
				} else {
					this.matrix[i][j] = this.matrix[i][j] / m.matrix[i][j];
				}
			}
		}

		return this;
	}
	

	// transpose matrix 
	public static Matrix transpose() {

		Matrix newMatrix = new Matrix(this.rows, this.columns);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				newMatrix.matrix[i][j] =  this.matrix[j][i];
			}
		}

		return newMatrix;

	}

	public static Matrix transpose(boolean modify) {

		Matrix newMatrix = new Matrix(this.rows, this.columns);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				newMatrix.matrix[i][j] =  this.matrix[j][i];
			}
		}

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.matrix[i][j] = newMatrix.matrix[i][j];
			}
		}

		return this;
	}


	// change element in matrix
	public static double[][] edit() {
		return this.matrix;
	}
}