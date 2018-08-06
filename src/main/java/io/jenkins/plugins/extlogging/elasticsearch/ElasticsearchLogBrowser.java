package io.jenkins.plugins.extlogging.elasticsearch;

import hudson.console.AnnotatedLargeText;
import io.jenkins.plugins.extlogging.api.ExternalLogBrowser;
import io.jenkins.plugins.extlogging.elasticsearch.util.ElasticSearchDao;
import jenkins.model.logging.Loggable;
import jenkins.model.logging.impl.BrokenAnnotatedLargeText;

import javax.annotation.CheckForNull;
import java.io.IOException;

/**
 * Log browser for Elasticsearch.
 * @author Oleg Nenashev
 * @since TODO
 */
public class ElasticsearchLogBrowser extends ExternalLogBrowser {

    //TODO(oleg-nenashev): jglick requests example of several implementations
    public ElasticsearchLogBrowser(Loggable loggable) {
        super(loggable);
    }

    //TODO: Cache values instead of refreshing them each time
    @Override
    public AnnotatedLargeText overallLog() {
        ElasticSearchDao dao;
        try {
            dao = ElasticsearchGlobalConfiguration.getInstance().toDao();
        } catch (Exception ex) {
            return new BrokenAnnotatedLargeText(ex);
        }

        return new ElasticsearchLogLargeTextProvider(dao, getOwner(), null).getLogText();
    }

    @Override
    public AnnotatedLargeText stepLog(@CheckForNull String stepId, boolean b) {
        ElasticSearchDao dao;
        try {
            dao = ElasticsearchGlobalConfiguration.getInstance().toDao();
        } catch (Exception ex) {
            return new BrokenAnnotatedLargeText(ex);
        }

        return new ElasticsearchLogLargeTextProvider(dao, getOwner(), stepId).getLogText();
    }

    @Override
    public boolean deleteLog() {
        // Not supported
        return false;
    }
}
