package hello.web.caching;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import net.sf.ehcache.config.FactoryConfiguration;
import net.sf.ehcache.distribution.jgroups.JGroupsCacheManagerPeerProviderFactory;

import static java.text.MessageFormat.format;

public class CacheManagerPeerProviderConfiguration extends FactoryConfiguration<CacheManagerPeerProviderConfiguration> {

    public CacheManagerPeerProviderConfiguration() {
        setClass(JGroupsCacheManagerPeerProviderFactory.class.getName());
        setProperties(format("connect={0}", getParameters()));
        setPropertySeparator("::");
    }

    private String getParameters() {
        return Joiner.on(":").join(ImmutableList.<String>builder()
                .add(format("UDP(mcast_addr={0};mcast_port={1};)", "231.12.21.132", "45566"))
                .add("PING")
                .add("MERGE2")
                .add("FD_SOCK")
                .add("VERIFY_SUSPECT")
                .add("pbcast.NAKACK")
                .add("UNICAST")
                .add("pbcast.STABLE")
                .add("FRAG")
                .add("pbcast.GMS")
                .build());
    }
}
