package com.gdsc.knu.service;

import com.gdsc.knu.dto.MarineApiResultDto;
import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.dto.response.MarinelifeUploadResponseDto;
import com.gdsc.knu.entity.MarineLife;
import com.gdsc.knu.repository.MarineLifeRepository;
import com.gdsc.knu.util.ConstVariables;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarineLifeService {
    private final MarineLifeRepository marineLifeRepository;
    private final GoogleAiService googleAiService;
    private final RankingService rankingService;
    private final ConstVariables constVariables;

    public MarinelifeUploadResponseDto processMarineImageAnalysis(GetImageResponseDto getImageResponseDto) {
        String prompt = """
                Requirements : analyze the image and provide "type : number" information.\s
                condition 1. Each entity must have type of marine animal. if entity not in Type, then mark as others. Type : [Baleen Whales (긴수염고래류) Toothed Whales (이빨고래류) Dolphins (돌고래류) Porpoises (돌고래과) Seals (물개류) Sea Lions (바다사자류) Walruses (바다코끼리) Manatees (매너티류) Dugongs (듀공) Polar Bears (북극곰) Sea Otters (바다수달) Marine Mustelids (해양 미어캣류) Penguins (펭귄류) Albatrosses (알바트로스류) Seagulls (갈매기류) Terns (제비갈매기류) Puffins (바다파랑새류) Pelicans (펠리컨류) Cormorants (가마우지류) Frigatebirds (해적새류) Skuas (스쿠아류) Shearwaters (쇠비오리류) Sharks (상어류) Rays (가오리류) Eels (장어류) Salmon (연어류) Tuna (참치류) Mackerel (고등어류) Cod (대구류) Flatfish (넙치류) Seahorses (해마류) Angelfish (천사어류) Butterflyfish (나비고기류) Clownfish (흰동가리류) Gobies (곰치류) Blennies (블레니류) Wrasses (랩류) Damselfish (돔류) Parrotfish (앵무고기류) Surgeonfish (독가시치류) Groupers (농어류) Snappers (우럭류) Barracuda (바라쿠다류) Lionfish (사자물고기류) Pufferfish (복어류) Moray Eels (먹장어류) Tarpons (타폰류) Bonefish (뼈대가리류) Catfish (메기류) Sardines (정어리류) Sea Turtles (바다거북류) Marine Iguanas (해양 이구아나) Saltwater Crocodiles (염수악어) Sea Snakes (바다뱀류) Jellyfish (해파리류) Octopuses (문어류) Squids (오징어류) Cuttlefish (갑오징어류) Corals (산호류) Sea Anemones (해마류) Starfish (불가사리류) Sea Urchins (성게류) Sea Cucumbers (해삼류) Lobsters (랍스터류) Crabs (게류) Shrimps (새우류) Barnacles (따개비류) Clams (조개류) Mussels (홍합류) Scallops (가리비류) Oysters (굴류) Snails (달팽이류) Nudibranchs (해루질류) Cephalopods (두족류) Arthropods (절지동물류) Echinoderms (극피동물류) Sponges (해면류) Worms (해양 벌레류) Brown Algae (갈조류) Red Algae (홍조류) Green Algae (녹조류) Seagrasses (해초류) Kelps (켈프류) Phytoplankton (식물성 플랑크톤) Plankton (플랑크톤류) Zooplankton (동물성 플랑크톤) Microbes (미생물류) Deep Sea Creatures (심해 생물) Hydrothermal Vent Communities (열수분출구 생태계) Cold Seep Communities (차가운 누출 생태계) Pelagic Zone Organisms (해양 표층 생물) Benthic Zone Organisms (저서 생물) Coral Reef Dwellers (산호초 서식 생물) Mangrove Forest Species (맹그로브 숲 종) Estuarine Species (하구 종) Polar Region Species (극지방 종)]
                condition 2. Each entity must have positive number of entity. ex) 1, 2, 3...
                condition 3. words in english.
                condition 4. don't include human or inanimate.
                                
                ex)
                String result = "{
                    "Toothed Whales" : 1,
                    "Dolphins" : 6,
                    "Sea Lions" : 23,
                    "Sea Otters" : 29,
                    "Albatrosses" : 5,
                    "Salmon" : 35,
                    "Tuna" : 4,
                    "Butterflyfish" : 21,
                    "Clownfish" : 8
                }";
                """;
        String apiEndpoint = constVariables.getAPI_URL() + constVariables.getAPI_KEY();

        String response = googleAiService.sendApiRequest(prompt, getImageResponseDto.getBase64EncodedImage(), apiEndpoint);

        MarineApiResultDto marineApiResultDto = new MarineApiResultDto(googleAiService.parseGoogleApiResponse(response).text);
        int total = marineLifeRepository.findByUserId(getImageResponseDto.getUserId()).stream().mapToInt(MarineLife::getScore).sum();
        registerMarinelifeScore(getImageResponseDto, marineApiResultDto);
        rankingService.createRanking(getImageResponseDto.getUserId(), marineApiResultDto.getScore());

        return new MarinelifeUploadResponseDto(marineApiResultDto.getMarineLife(), marineApiResultDto.getScore(), total);
    }

    public void registerMarinelifeScore(GetImageResponseDto getImageResponseDto, MarineApiResultDto marineApiResultDto) {
        MarineLife marineLife = new MarineLife();

        marineLife.setUserId(getImageResponseDto.getUserId());
        marineLife.setScore(marineApiResultDto.getScore());
        marineLife.setClassName(marineApiResultDto.getMarineLife().stream().map(MarinelifeUploadResponseDto.MarinelifeEntity::getName).reduce((a, b) -> a + "," + b).orElse(""));
        marineLife.setImageId(getImageResponseDto.getMediaFileId());
        marineLifeRepository.save(marineLife);
    }
}
