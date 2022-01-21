/* Licensed under MIT 2022. */
package edu.kit.kastel.mcse.ardoco.addclass;

/**
 * @author Jan Keim
 *
 */
public enum ArchitecturalDesignDecision {
    NO_DESIGN_DECISION(null), //
    DESIGN_DECISION(null), //
    //
    EXISTENCE_DECISION(DESIGN_DECISION), //
    ///
    STRUCTURAL_DECISION(EXISTENCE_DECISION), //
    EXTRA_SYSTEMIC(STRUCTURAL_DECISION), DATA_FILE(EXTRA_SYSTEMIC), INTEGRATION(EXTRA_SYSTEMIC), //
    INTRA_SYSTEMIC(STRUCTURAL_DECISION), INTERFACE(INTRA_SYSTEMIC), COMPONENT(INTRA_SYSTEMIC), //
    CLASS_RELATED(INTRA_SYSTEMIC), ASSOCIATION(CLASS_RELATED), CLASS(CLASS_RELATED), INHERITANCE(CLASS_RELATED), //
    ///
    ARRANGEMENT_DECISION(EXISTENCE_DECISION), //
    ARCHITECTURAL_STYLE(ARRANGEMENT_DECISION), //
    ARCHITECTURAL_PATTERN(ARRANGEMENT_DECISION), //
    REFERENCE_ARCHITECTURE(ARRANGEMENT_DECISION), //
    ///
    BEHAVIORAL_DECISION(EXISTENCE_DECISION), //
    RELATION(BEHAVIORAL_DECISION), //
    FUNCTION(BEHAVIORAL_DECISION), //
    ALGORITHM(BEHAVIORAL_DECISION), //
    MESSAGING(BEHAVIORAL_DECISION), //
    //
    PROPERTY_DECISION(DESIGN_DECISION), //
    GUIDELINE(PROPERTY_DECISION), //
    DESIGN_RULE(PROPERTY_DECISION), //
    //
    EXECUTIVE_DECISION(DESIGN_DECISION), //
    ORGANIZATIONAL_PROCESS_RELATED(EXECUTIVE_DECISION), //
    TECHNOLOGICAL(EXECUTIVE_DECISION), //
    TOOL(TECHNOLOGICAL), //
    DATA_BASE(TECHNOLOGICAL), //
    PLATFORM(TECHNOLOGICAL), //
    BOUNDARY_INTERFACE(TECHNOLOGICAL), USER_INTERFACE(BOUNDARY_INTERFACE), API(BOUNDARY_INTERFACE), //
    PROGRAMMING_LANGUAGE(TECHNOLOGICAL), //
    FRAMEWORK(TECHNOLOGICAL), //

    ;

    private ArchitecturalDesignDecision parent;

    ArchitecturalDesignDecision(ArchitecturalDesignDecision parent) {
        this.parent = parent;
    }

    public boolean isContainedIn(ArchitecturalDesignDecision other) {
        if (other == this) {
            return true;
        }
        var ancestor = parent;
        while (ancestor != null) {
            if (ancestor == other) {
                return true;
            }
            ancestor = ancestor.parent;
        }
        return false;
    }

}
