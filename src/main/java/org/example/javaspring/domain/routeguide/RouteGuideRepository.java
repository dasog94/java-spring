package org.example.javaspring.domain.routeguide;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static org.example.javaspring.domain.routeguide.RouteGuideUtil.getDefaultFeaturesFile;
import static org.example.javaspring.domain.routeguide.RouteGuideUtil.parseFeatures;

@Repository
@RequiredArgsConstructor
public class RouteGuideRepository {

    private final ObjectMapper objectMapper;

    private Collection<Feature> features;

    @PostConstruct
    public void init() throws IOException {
        this.features = parseFeatures(getDefaultFeaturesFile());
    }

    private Collection<Feature> loadFeaturesFromJson(String filePath) {
        try {
            String jsonContent = Files.readString(Paths.get(filePath));
            return objectMapper.readValue(jsonContent, new TypeReference<List<Feature>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load features from JSON file", e);
        }
    }

    public Collection<Feature> getFeatures() {
        return features;
    }
}
