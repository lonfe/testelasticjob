import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class MySimpleJob implements SimpleJob {
    public void execute(ShardingContext context) {
        System.out.println("shardingTotalCount:" + context.getShardingTotalCount());
        System.out.println("jobParameter:" + context.getJobParameter());
        switch(context.getShardingItem()) {
            case 0:
                System.out.println("00000000000000");
                System.out.println(context.getShardingParameter());
                break;
            case 1:
                System.out.println("11111111111111");
                System.out.println(context.getShardingParameter());
                break;
            case 2:
                System.out.println("22222222222222");
                System.out.println(context.getShardingParameter());
                break;
        }
    }
}
