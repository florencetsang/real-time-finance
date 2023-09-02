package com.github.florencetsang.realtimefinance.converters;

import org.apache.kafka.connect.source.SourceRecord;

public interface SourceRecordConverter<Thing> {

    SourceRecord convert(Thing thing);

}
