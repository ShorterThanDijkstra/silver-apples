package process;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import entity.TheIntro;
import entity.Unit;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Book2Json implements BookWriter {
    private SequenceWriter sequenceWriter;

    private SequenceWriter getTheSequenceWriter() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        mapper = mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper = mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        FileWriter fileWriter = new FileWriter(TheBookProcessor.WMVB_FILE_UNITS, false);
        return mapper.writer().writeValuesAsArray(fileWriter);
    }

    @Override
    public void writeIntro(TheIntro intro) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        FileOutputStream stream = new FileOutputStream(TheBookProcessor.WMVB_FILE_INTRO);
        objectMapper.writeValue(stream, intro);
        stream.close();
    }

    @Override
    public void writeUnit(Unit unit) throws IOException {
        if (sequenceWriter == null) {
            sequenceWriter = getTheSequenceWriter();
        }
        sequenceWriter.write(unit);
    }

    @Override
    public void writeUnitsDone() throws IOException {
        if (sequenceWriter != null) {
            sequenceWriter.close();
        }
    }
}
