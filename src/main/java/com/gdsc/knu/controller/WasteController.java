package com.gdsc.knu.controller;

import com.gdsc.knu.entity.Waste;
import com.gdsc.knu.repository.WasteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/wastes")
public class WasteController {
    private final WasteRepository wasteRepository;

    public WasteController(WasteRepository wasteRepository) {
        this.wasteRepository = wasteRepository;
    }

    @PostMapping
    public ResponseEntity<Waste> createWaste(@RequestBody Waste newWaste) {
        Optional<Waste> existingWaste = wasteRepository.findById(newWaste.getUserId());

        if (((Optional<?>) existingWaste).isPresent()) {
            Waste wasteToUpdate = existingWaste.get();
            wasteToUpdate.setPlastic(wasteToUpdate.getPlastic() + newWaste.getPlastic());
            wasteToUpdate.setStyrofoam(wasteToUpdate.getStyrofoam() + newWaste.getStyrofoam());
            wasteToUpdate.setFiber(wasteToUpdate.getFiber() + newWaste.getFiber());
            wasteToUpdate.setVinyl(wasteToUpdate.getVinyl() + newWaste.getVinyl());
            wasteToUpdate.setGeneralWaste(wasteToUpdate.getGeneralWaste() + newWaste.getGeneralWaste());

            Waste updatedWaste = wasteRepository.save(wasteToUpdate);
            return ResponseEntity.ok(updatedWaste);
        } else {
            newWaste.setWasteId(null);
            Waste createdWaste = wasteRepository.save(newWaste);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWaste);
        }
    }

}
