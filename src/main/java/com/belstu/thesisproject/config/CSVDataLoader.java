package com.belstu.thesisproject.config;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CSVDataLoader {
  @SuppressWarnings("Used deprecated method. Temporary solution")
  public <T> List<T> loadObjectList(Class<T> type, String fileName) {
    try {
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
      CsvMapper mapper = new CsvMapper();
      File file = new ClassPathResource(fileName).getFile();
      MappingIterator<T> readValues = mapper.reader(type).with(bootstrapSchema).readValues(file);
      return readValues.readAll();
    } catch (Exception e) {
      log.error("Error occurred while loading object list from file " + fileName, e);
      return Collections.emptyList();
    }
  }

  @SuppressWarnings("Used deprecated method. Temporary solution")
  public List<String[]> loadManyToManyRelationship(String fileName) {
    try {
      CsvMapper mapper = new CsvMapper();
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
      mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
      File file = new ClassPathResource(fileName).getFile();
      MappingIterator<String[]> readValues =
          mapper.reader(String[].class).with(bootstrapSchema).readValues(file);
      return readValues.readAll();
    } catch (Exception e) {
      log.error(
          "Error occurred while loading many to many relationship from file = " + fileName, e);
      return Collections.emptyList();
    }
  }
}
