# Rulebook prototype

Prototype for a rule based validation service

Aims to validate json documents using rule and convert between spreadsheets and JSON documents.

This is not intended as production code and is missing many sensible features.

## Questions we are trying to answer

1. Can we load custom validators from the classpath? (yes, using reflection)
1. Can we describe the validator config in json, without using a standard config object (yes, using a custom deserialiser and @JsonRawValue)
1. Can we use JSONpath to decide when a group rules should be applied? (yes, through the rule group condition)
1. Can we use JSONpath to decide which parts of a document should be validated? (yes, but beware of different return types from jayway)
1. Can we make a spreadsheet based on the rules? (yes)
1. Can we use the JSONpath to convert a spreadsheet back to a more structured document. 
