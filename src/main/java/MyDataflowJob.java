import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.ArrayList;
import java.util.List;

public class MyDataflowJob implements DataflowJob {
    public List fetchData(ShardingContext context) {
        System.out.println("shardingTotalCount:" + context.getShardingTotalCount());
        System.out.println("jobParameter:" + context.getJobParameter());
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        return list;
    }

    public void processData(ShardingContext shardingContext, List data) {
        System.out.println(data);
    }
}
