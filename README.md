cristal-showcase
================

This repository includes several projects to showcase different features of [CRISTAL](http://github.com/isa-group/cristal), namely:

## ral-showcase

This project illustrates several features of RAL such as its design-time analysis.

## ram2bpmn-showcase

This project illustrates the RAM2BPMN approach to enable current BPMSs to execute BPMN processes in which people with different responsibilities collaborate to complete process activities. The core idea is to take a BPMN model without resource-related information and a Resource Assignment Matrix as inputs and to automatically generate a new BPMN model in which the only responsibility defined for each activity is Responsible, but which includes new activities to model the semantics conveyed by the other responsibilities included in the matrix. 

More specifically, RAM2BPMN turns every activity for which some type of responsibility different than Responsible is defined into a subprocess. We refer to the subprocesses created during the transformation as RAM subprocesses. A RAM subprocess is a regular BPMN subprocess that includes the specific tasks for all the responsibilities that people may have during the execution of the activity of the original process. RAM subprocesses are created from collaboration templates. A collaboration template specifies how people with different responsibilities interact with each other to carry out an activity of a process. The collaboration template used is chosen at design-time amongst a library of templates depending on the specific requirements of the activity.

This showcase is an example on how the RAM2BPMN approach works. Specifically, it allows the user to:
- Upload a regular BPMN model without resource-related information.
- Define a RASCI matrix (a kind of Resource Assignment Matrix) for that process.
- Select the collaboration templates that will be applied for each activity of the process from a library of templates.
- Generate the resulting BPMN model after applying RAM2BPMN.

To test this project, you just need to clone the repository and run:

```
$ cd ram2bpmn-showcase
$ mvn spring-boot:run
```
