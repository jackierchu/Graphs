public static int max(int[] a) {
    int maximum = a[0];
    for (int i=1; i<a.length; i++) {
        if (a[i] > maximum) {
            maximum = a[i];
        }
    }
    return maximum;
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public ArrayList<ArrayList<Integer>> threeSum(int[] a) {
    ArrayList<ArrayList<Integer>> final = new ArrayList<>();

    if(a == null || a.length<3)
        return final;

    Arrays.sort(a);

    for(int i=0; i<a.length-2; i++){
        if(i==0 || a[i] > a[i-1]){
            int j=i+1;
            int k=a.length-1;

            while(j<k){
                if(a[i]+a[j]+a[k]==0){
                    ArrayList<Integer> l = new ArrayList<Integer>();
                    l.add(a[i]);
                    l.add(a[j]);
                    l.add(a[k]);
                    final.add(l);

                    j = j+1;
                    k = k-1;

                } else if(a[i]+a[j]+a[k]<0){
                    j = j+1;
                } else{
                    k = k-1;
                }
              }
             }

           }

    return final;
}

public ArrayList<ArrayList<Integer>> threeSumDistinct(int[] a) {
    ArrayList<ArrayList<Integer>> final = new ArrayList<>();

    if(a == null || a.length<3)
        return final;

    Arrays.sort(a);

    for(int i=0; i<a.length-2; i++){
        if(i==0 || a[i] > a[i-1]){
            int j=i+1;
            int k=a.length-1;

            while(j<k){
                if(a[i]+a[j]+a[k]==0){
                    ArrayList<Integer> l = new ArrayList<Integer>();
                    l.add(a[i]);
                    l.add(a[j]);
                    l.add(a[k]);
                    final.add(l);

                    j = j+1;
                    k = k-1;

                    while(j<k && a[j]==a[j-1])
                        j = j+1;
                    while(j<k && a[k]==a[k+1])
                        k = k-1;

                } else if(a[i]+a[j]+a[k]<0){
                    j = j+1;
                } else{
                    k = k-1;
                }
              }
             }

           }

    return final;
}
