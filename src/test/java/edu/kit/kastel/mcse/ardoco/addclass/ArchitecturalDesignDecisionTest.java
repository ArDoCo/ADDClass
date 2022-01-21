package edu.kit.kastel.mcse.ardoco.addclass;

import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.ARRANGEMENT_DECISION;
import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.BEHAVIORAL_DECISION;
import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.DESIGN_DECISION;
import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.EXECUTIVE_DECISION;
import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.EXISTENCE_DECISION;
import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.NO_DESIGN_DECISION;
import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.PROPERTY_DECISION;
import static edu.kit.kastel.mcse.ardoco.addclass.ArchitecturalDesignDecision.STRUCTURAL_DECISION;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArchitecturalDesignDecisionTest {

    @Test
    @DisplayName("Test hierarchy for sub-classes of decision")
    void hierarchyDesignDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertFalse(NO_DESIGN_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(DESIGN_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(EXISTENCE_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(STRUCTURAL_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INTRA_SYSTEMIC.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.COMPONENT.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.CLASS.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ARRANGEMENT_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ARCHITECTURAL_STYLE.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ARCHITECTURAL_PATTERN.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.REFERENCE_ARCHITECTURE.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(BEHAVIORAL_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.FUNCTION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(PROPERTY_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.GUIDELINE.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(EXECUTIVE_DECISION.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.TECHNOLOGICAL.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ORGANIZATIONAL_PROCESS_RELATED.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.BOUNDARY_INTERFACE.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.API.isContainedIn(DESIGN_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.FRAMEWORK.isContainedIn(DESIGN_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test no design decision hierarchy")
    void hierarchyNoDesignDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertTrue(NO_DESIGN_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(DESIGN_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(EXISTENCE_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(STRUCTURAL_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.INTRA_SYSTEMIC.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.COMPONENT.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.CLASS.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ARRANGEMENT_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.ARCHITECTURAL_STYLE.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.ARCHITECTURAL_PATTERN.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.REFERENCE_ARCHITECTURE.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(BEHAVIORAL_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.FUNCTION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(PROPERTY_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.GUIDELINE.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(EXECUTIVE_DECISION.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.TECHNOLOGICAL.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.ORGANIZATIONAL_PROCESS_RELATED.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.BOUNDARY_INTERFACE.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.API.isContainedIn(NO_DESIGN_DECISION)), //
                () -> Assertions.assertFalse(ArchitecturalDesignDecision.FRAMEWORK.isContainedIn(NO_DESIGN_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test hierarchy for sub-classes of existence decision")
    void hierarchyExistenceDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertTrue(EXISTENCE_DECISION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(STRUCTURAL_DECISION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.EXTRA_SYSTEMIC.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INTRA_SYSTEMIC.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.CLASS_RELATED.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.DATA_FILE.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INTEGRATION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INTERFACE.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.COMPONENT.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ASSOCIATION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.CLASS.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INHERITANCE.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ARRANGEMENT_DECISION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ARCHITECTURAL_STYLE.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ARCHITECTURAL_PATTERN.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.REFERENCE_ARCHITECTURE.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(BEHAVIORAL_DECISION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.RELATION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.FUNCTION.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ALGORITHM.isContainedIn(EXISTENCE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.MESSAGING.isContainedIn(EXISTENCE_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test hierarchy for sub-classes of structural decision")
    void hierarchyStructuralDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertTrue(STRUCTURAL_DECISION.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.EXTRA_SYSTEMIC.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INTRA_SYSTEMIC.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.CLASS_RELATED.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.DATA_FILE.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INTEGRATION.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INTERFACE.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.COMPONENT.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ASSOCIATION.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.CLASS.isContainedIn(STRUCTURAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.INHERITANCE.isContainedIn(STRUCTURAL_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test hierarchy for sub-classes of arrangement decision")
    void hierarchyArrangementDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertTrue(ARRANGEMENT_DECISION.isContainedIn(ARRANGEMENT_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ARCHITECTURAL_STYLE.isContainedIn(ARRANGEMENT_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ARCHITECTURAL_PATTERN.isContainedIn(ARRANGEMENT_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.REFERENCE_ARCHITECTURE.isContainedIn(ARRANGEMENT_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test hierarchy for sub-classes of behavioral decision")
    void hierarchyBehavioralDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertTrue(BEHAVIORAL_DECISION.isContainedIn(BEHAVIORAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.RELATION.isContainedIn(BEHAVIORAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.FUNCTION.isContainedIn(BEHAVIORAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ALGORITHM.isContainedIn(BEHAVIORAL_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.MESSAGING.isContainedIn(BEHAVIORAL_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test hierarchy for sub-classes of property decision")
    void hierarchyPropertyDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertTrue(PROPERTY_DECISION.isContainedIn(PROPERTY_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.GUIDELINE.isContainedIn(PROPERTY_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.DESIGN_RULE.isContainedIn(PROPERTY_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test hierarchy for sub-classes of arrangement decision")
    void hierarchyExecutiveDecisionTest() {
        Assertions.assertAll(//
                () -> Assertions.assertTrue(EXECUTIVE_DECISION.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.TECHNOLOGICAL.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.ORGANIZATIONAL_PROCESS_RELATED.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.TOOL.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.DATA_BASE.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.PLATFORM.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.BOUNDARY_INTERFACE.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.USER_INTERFACE.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.API.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.PROGRAMMING_LANGUAGE.isContainedIn(EXECUTIVE_DECISION)), //
                () -> Assertions.assertTrue(ArchitecturalDesignDecision.FRAMEWORK.isContainedIn(EXECUTIVE_DECISION)) //
        );
    }

    @Test
    @DisplayName("Test level calculation")
    void levelTest() {
        Assertions.assertAll(//
                () -> Assertions.assertEquals(0, DESIGN_DECISION.getLevel()), //
                () -> Assertions.assertEquals(0, NO_DESIGN_DECISION.getLevel()), //
                () -> Assertions.assertEquals(1, EXISTENCE_DECISION.getLevel()), //
                () -> Assertions.assertEquals(2, STRUCTURAL_DECISION.getLevel()), //
                () -> Assertions.assertEquals(4, ArchitecturalDesignDecision.COMPONENT.getLevel()), //
                () -> Assertions.assertEquals(3, ArchitecturalDesignDecision.RELATION.getLevel()), //
                () -> Assertions.assertEquals(5, ArchitecturalDesignDecision.CLASS.getLevel()), //
                () -> Assertions.assertEquals(4, ArchitecturalDesignDecision.API.getLevel()) //
        );
    }
}
