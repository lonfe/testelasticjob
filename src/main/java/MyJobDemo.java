import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobRootConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class MyJobDemo {
    public static void main(String[] args) {
        JobScheduler jobScheduler = new JobScheduler(createRegistryCenter(), createDataflowJobConfiguration());
        jobScheduler.init();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("192.168.2.16:2181", "my-job"));
        regCenter.init();
        return regCenter;
    }

    private static LiteJobConfiguration createSimpleJobConfiguration() {
        // 定义作业核心配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("mySimpleJob", "0/3 * * * * ?", 3)
                .jobParameter("s").shardingItemParameters("0=a,1=b,2=c").build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MySimpleJob.class.getCanonicalName());
        // 定义Lite作业根配置
        JobRootConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true).build();
        return (LiteJobConfiguration) simpleJobRootConfig;

    }

    private static LiteJobConfiguration createDataflowJobConfiguration() {
        JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder("myDataflowJob", "0/6 * * * * ?", 3)
                .build();
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(dataflowCoreConfig, MyDataflowJob.class.getCanonicalName(), true);
        JobRootConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(true).build();
        return (LiteJobConfiguration) dataflowJobRootConfig;
    }
}