package com.main.maring.util.mar;

public class EnvironmentConfig {

    // Constants for system parameters
    public static final double k1 = 0.0008;
    public static final double k2 = 0.0008;
    public static final double k3 = 0.00005;
    public static final double k4 = 0.0001;
    public static final double k5 = 0.0002;
    public static final double k6 = 0.00002;
    public static final double k7 = 0.0004;
    public static final double k8 = 0.008;
    public static final double k9 = 0.007;
    public static final double k10 = 0.0004;
    public static final double k11 = 0.000025;
    public static final double k12 = 0.03;
    public static final double k13 = 0.00002;

    // Initial state [湿度, 氧气, 压力, 温度, 磁场]
    public static final double[] x0 = {0d, 0d, 6d, -46d, 0d};

    // State-space matrix A (5x5 matrix)
    public static final double[][] A = {
        {-k1, 0, 0, k2, 0},
        {-k3, -k4, 0, -k5, 0},
        {k6, 0, -k7, -k8, k9},
        {0, 0, k11, -k10, 0},
        {0, 0, 0, 0, -k13}
    };
    
    public static final double[] xd = calculateDeviation();

    // This method calculates the deviation vector (xd) using A and x0
    public static double[] calculateDeviation() {
        double[] xd = new double[5];
        for (int i = 0; i < 5; i++) {
            xd[i] = 0;
            for (int j = 0; j < 5; j++) {
                xd[i] += A[i][j] * x0[j];
            }
            xd[i] = -xd[i];  // Apply the negative sign
        }
        return xd;
    }
    
    /**
     * 需要传入上一状态
     * */
    public static double[] next(double[] x) {
    	double[] xout = new double[5];
    	for (int i = 0; i < 5; i++) {
    		xout[i] = x[i];
            for (int j = 0; j < 5; j++) {
                xout[i] += A[i][j] * x[j];
            }
            xout[i] += xd[i];
        }
    	if(xout[0]<0) xout[0] = 0d;
    	if(xout[0]>200) xout[0] = 200d;
    	if(xout[1]<0) xout[1] = 0d;
    	if(xout[1]>100) xout[1] = 100d;
    	if(xout[2]<0) xout[2] = 0d;
    	if(xout[2]>6400) xout[2] = 6400d;
    	if(xout[3]<-273) xout[3] = -273d;
    	if(xout[3]>500) xout[3] = 500d;
    	if(xout[4]<0) xout[4] = 0d;
    	if(xout[4]>200) xout[4] = 200d;
        return xout;
    }

}
