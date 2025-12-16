package lab1;

import Utils.PercolationModel;

public class KnotPercolationModel {

    static void gridModeling(int L, double p) {
        PercolationModel pm = new PercolationModel(L);
        pm.genKnotGrid(p);
        System.out.println("Фактическое начение концентрации: " + pm.GetActualConcentration());
        pm.printKnotGrid();
    }

    
    public static void testModel(int L, double p, int testsCount) {
        double totalConcentration = 0.0;

        for (int i=0; i<testsCount; i++) {
            System.out.println("\nТест №" + (i+1));
            PercolationModel pm = new PercolationModel(L);
            pm.genKnotGrid(p);
            pm.printKnotGrid();
            totalConcentration += pm.GetActualConcentration();
        }

        double averageConcentration = totalConcentration / testsCount;
        System.out.println("\nСредняя фактическое концентрация: " + averageConcentration);
    }


    static int scott(int n, int d) {
        return (int) Math.pow(Math.pow(2*n/3, 1/(d*3)) + 1, d);
    }


    public static double getPirson(int L, double p, int testsCount) {
        int k = scott(L*L, 2);
        int b = (int)Math.sqrt(L*L/k);
        double xiCommon = 0.0;

        for(int test=0; test<testsCount; test++) {
            PercolationModel pm = new PercolationModel(L);
            pm.genKnotGrid(p);
            double xi = 0.0;
            for(int i=0; i<L; i+=b) {
                for(int j=0; j<L; j+=b) {
                    int n_i = 0;
                    double E_i = Math.min(L-i, b) * Math.min(L-j, b) * p;
                    for(int bi=i; bi < i+b && bi<L; bi++) {
                        for(int bj=j; bj < j+b && bj<L; bj++) {
                            if (pm.GetKnotGrid()[bi][bj] == 1) {
                                n_i++;
                            }
                        }
                    }
                    xi += Math.pow(n_i - E_i, 2) / E_i;
                }
            }
            xiCommon += xi;
        }
        return xiCommon / testsCount;
    }
}

