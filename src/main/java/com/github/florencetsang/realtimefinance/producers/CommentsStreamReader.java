package com.github.florencetsang.realtimefinance.producers;

import com.github.florencetsang.realtimefinance.converters.CommentSourceRecordConverter;
import net.dean.jraw.models.Comment;
import net.dean.jraw.pagination.Stream;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommentsStreamReader extends StreamReader<Comment> {

    private final CommentSourceRecordConverter recordConverter;

    public CommentsStreamReader(
            Map<Map<String, Object>, Map<String, Object>> offsets,
            Stream<Comment> stream,
            Consumer<Throwable> onError,
            List<String> subreddits,
            String topic
    ) {
        super(offsets, stream, onError, "comment", subreddits);
        this.recordConverter = new CommentSourceRecordConverter(topic);
    }

    @Override
    protected SourceRecord convertThing(Comment comment) {
        return recordConverter.convert(comment);
    }

    @Override
    protected String subredditForThing(Comment comment) {
        return comment.getSubreddit();
    }

    @Override
    protected Map<String, Object> partitionForSubreddit(String subreddit) {
        return recordConverter.sourcePartition(subreddit);
    }

    @Override
    protected Date dateForThing(Comment comment) {
        return comment.getCreated();
    }
}
