package average;

public class Average {
    /**
     * Returns the average of an array of numbers
     * 
     * @param the array of integer numbers
     * @return the average of the numbers
     */
    public float computeAverage(int[] nums) {
        float result = 0;
        for (int i = 0; i < nums.length; i++) {
            result += nums[i];
        }
        return result / nums.length;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};

        Average NewAverage = new Average();

        float result = NewAverage.computeAverage(nums);

        System.out.println(result);
    }
}
