package com.lifeng.commons.elasticsearch;

import com.google.common.base.Preconditions;
import com.lifeng.commons.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.util.Properties;

import static com.lifeng.commons.util.StringUtil.split;

/**
 * Created by lifeng on 2018/5/21.
 * essearch客户端
 */
public class ElasticSearchClient implements DisposableBean,InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchClient.class);
    private final String clusterNodes;
    private final String clusterName;
    private Boolean clientTransportSniff = Boolean.valueOf(true);
    private Boolean clientIgnoreClusterName = Boolean.FALSE;
    private String clientPingTimeout = "5s";
    private String clientNodesSamplerInterval = "5s";
    private TransportClient client;
    private Properties properties;
    static final String COLON = ":";
    static final String COMMA = ",";

    public ElasticSearchClient(String clusterNodes,String clusterName){
        this.clusterNodes = clusterNodes;
        this.clusterName = clusterName;
    }
    @Override
    public void destroy() throws Exception {
        client.close();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        client = new PreBuiltTransportClient(settings());
        Preconditions.checkArgument(StringUtil.isNotEmpty(clusterNodes),
                "[Assertion failed] clusterNodes settings missing.");
        for (String clusterNode : split(clusterNodes, COMMA))
        {
            String hostName = StringUtils.substringBeforeLast(clusterNode, COLON);
            String port = StringUtils.substringAfterLast(clusterNode, COLON);

            logger.info("adding transport node : " + clusterNode);
            client.addTransportAddress(
                    new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port)));
        }
        client.connectedNodes();

    }

    private Settings settings()
    {
        if (properties != null)
        {
            return Settings.builder().put(properties).build();
        }
        return Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", clientTransportSniff)
                .put("client.transport.ignore_cluster_name", clientIgnoreClusterName)
                .put("client.transport.ping_timeout", clientPingTimeout)
                .put("client.transport.nodes_sampler_interval", clientNodesSamplerInterval).build();
    }
    public void setClientTransportSniff(Boolean clientTransportSniff)
    {
        this.clientTransportSniff = clientTransportSniff;
    }

    public String getClientNodesSamplerInterval()
    {
        return clientNodesSamplerInterval;
    }

    public void setClientNodesSamplerInterval(String clientNodesSamplerInterval)
    {
        this.clientNodesSamplerInterval = clientNodesSamplerInterval;
    }

    public String getClientPingTimeout()
    {
        return clientPingTimeout;
    }

    public void setClientPingTimeout(String clientPingTimeout)
    {
        this.clientPingTimeout = clientPingTimeout;
    }

    public Boolean getClientIgnoreClusterName()
    {
        return clientIgnoreClusterName;
    }

    public void setClientIgnoreClusterName(Boolean clientIgnoreClusterName)
    {
        this.clientIgnoreClusterName = clientIgnoreClusterName;
    }

    public void setProperties(Properties properties)
    {
        this.properties = properties;
    }

    public TransportClient getClient(){
        return client;
    }


}
