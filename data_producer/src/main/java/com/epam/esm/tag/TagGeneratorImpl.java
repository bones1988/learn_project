package com.epam.esm.tag;

public class TagGeneratorImpl implements TagGenerator {
    private static final String NAME_FOR_CORRECT_TAGS = "correct";

    @Override
    public Tag generateTag(String namePostfix) {
        Tag tag = new Tag();
        tag.setName(NAME_FOR_CORRECT_TAGS+namePostfix);
        return tag;
    }
}
