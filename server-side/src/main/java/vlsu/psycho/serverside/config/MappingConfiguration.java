package vlsu.psycho.serverside.config;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public class MappingConfiguration {
}
