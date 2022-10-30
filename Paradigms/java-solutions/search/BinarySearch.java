package search;

public class BinarySearch {
//  Pred: l >= -1 && r <= arr.lenght && l < r && arr[i] >= arr[i + 1] && arr.lenght > 0
//  Post: min r` : arr[r`] <= x
    public static int recursion(int[] arr, int x, int l, int r) {
        // l >= -1 && r <= arr.lenght && l < r
        // l >= -1 && r <= arr.lenght && r - l > 0
        // l >= -1 && r <= arr.lenght && r - l > 0 && arr.lenght > 0
        // l >= -1 && r <= arr.lenght && r - l > 1
        if (r - l <= 1) {
            return r;
        }
        // l >= -1 && r <= arr.lenght && r - l > 1
        int mid = (l + r) / 2;
        // mid = (l + r) / 2 && l != r
        // mid = (l + r) / 2`&& mid > l && mid < r
        if (arr[mid] > x){
            // mid > l && arr[mid] > x && arr[i] >= arr[i + 1]
            // arr[l] >= arr[mid] && arr[mid] > x && l >= 0 || l < 0
            // arr[l] > x && l >= 0 || l < 0
            return recursion(arr, x, mid, r);
        } else {
            // mid < r && arr[mid] <= x && arr[i] >= arr[i + 1]
            // arr[r] <= arr[mid] && arr[mid] <= x && r < arr.lenght || r >= arr.lenght
            // arr[r] <= x && r < arr.lenght || r >= arr.lenght
            return recursion(arr, x, l, mid);
        }
    }

//  Pred: arr[i] >= arr[i + 1] && arr.lenght > 0
//  Post: min r` : arr[r`] <= x
    public static int iterative(int[] arr, int x) {
        int l = -1, r = arr.length;
        // l == -1 && r == arr.lenght && -1 < arr.lenght
        // l == -1 && r == arr.lenght && l < r
        // l == -1 && r == arr.lenght && r - l > 0 && r > 0 && l == -1
        // l == -1 && r == arr.lenght && r - l > 1
        while (r - l > 1) {
            // r > l && r <= arr.lenght && l >= -1
            int mid = (l + r) / 2;
            // mid = (l + r) / 2 && r != l
            // mid = (l + r) / 2 && mid > l && mid < r
            if (arr[mid] > x) {
                // arr[mid] > x && mid > l && arr[i] >= arr[i + 1]
                // arr[l] >= arr[mid] && arr[mid] > x && l >= 0 || l < 0
                // arr[l] > x && l >= 0 || l < 0
                l = mid;
                // l` == mid && l` > l
                // arr[l] >= arr[l`] && arr[l`] > x && l >= 0 || l < 0
                // arr[l] > x && l >= 0 || l < 0
            } else {
                // arr[mid] <= x && mid < r && arr[i] >= arr[i + 1]
                // arr[r] <= arr[mid] && arr[mid] <= x && r < arr.lenght || r >= arr.lenght
                // arr[r] <= x && r < arr.lenght || r >= arr.lenght
                r = mid;
                // r` == mid && r` < r
                // arr[r] <= arr[r`] && arr[r`] <= x && r < arr.lenght || r >= arr.lenght
                // arr[r] <= x && r < arr.lenght || r >= arr.lenght
            }
        }
        return r;
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] arr = new int[args.length - 1];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(args[i + 1]);
        }

        System.out.println(iterative(arr, x));
    }
}
