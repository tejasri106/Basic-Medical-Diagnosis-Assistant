# Basic-Medical-Diagnosis-Assistant

**Medical Diagnosis Assistant**

Welcome to the Medical Diagnosis Assistant, it's a basic, interactive Java-based tool designed to suggest possible medical conditions based on your symptoms. 
By answering a series of yes/no questions, the program will guide you through a diagnosis. If the diagnosis is incorrect, the assistant will learn from your feedback 
and improve over time.

⚠️ Note: This is a basic diagnostic assistant. It is not a substitute for professional medical advice. Always consult a licensed healthcare provider for 
accurate diagnosis and treatment.

**Features**

Interactive Diagnosis: Step through questions to receive a diagnosis.

Learning Ability: The assistant learns from incorrect diagnoses by adding new conditions and distinguishing questions to the decision tree.

Simple GUI: User-friendly interface built with Java Swing.

"I'm Not Sure" Option: For users who are uncertain about the diagnosis, the assistant acknowledges uncertainty and suggests consulting a doctor.

Automatic Saving: Updates the diagnosis tree (diagnosis_tree.txt) with new information.

**How It Works**

The assistant asks a series of yes/no questions.

Based on your answers, it will suggest a diagnosis.

If the diagnosis is correct, the session ends.

If the diagnosis is incorrect, you have the option to help the assistant learn.

You can also select "I'm Not Sure" if you are uncertain about the diagnosis.

All updates are saved to improve future suggestions.

**Installation Instructions**

Clone the Repository
git clone https://github.com/tejasri106/Basic-Medical-Diagnosis-Assistant.git
Navigate to the Project Directory:
cd MedicalDiagnosisAssistant
Compile the Code:
javac *.java
Run the Program:
java MedicalDiagnosisTree


**Example Walkthrough**

Start:
Do you have a fever? (Yes/No)

Follow-Up:
Do you have a rash? (Yes/No)

Diagnosis:
Diagnosis: Measles
Is this correct? (Yes/No/I'm Not Sure)
If Yes → Great! The assistant is happy to help.

If No → You can help the assistant learn a better diagnosis.

If I'm Not Sure → The assistant recommends consulting a doctor.

**File Structure**

MedicalDiagnosisAssistant/
├── Diagnosis.java
├── MedicalDiagnosisTree.java
├── TreeNode.java
├── diagnosis_tree.txt
├── DiagnosisTree.java

Diagnosis.java: The graphical interface using Java Swing.
MedicalDiagnosisTree.java: Manages the decision tree and starts the diagnosis.
TreeNode.java: Defines the decision tree structure.
diagnosis_tree.txt: Stores the diagnosis tree in a readable format using Q: and A: prefixes. Each Q, has 2 A's as yes and no nodes. 
New question and answers can be added at appropriate areas.
DiagnosisTree.java: Non-graphical interface.

**Future Advancements**

This assistant is currently a basic implementation, but there are several ways it can be expanded and improved:

Symptom Severity and Length:
Instead of a simple yes/no answer, the assistant could ask about the severity and duration of symptoms for a more accurate diagnosis.

Follow-Up Questions:
After a user answers Yes or No, additional follow-up questions could be asked to narrow down conditions further.

Multi-Symptom Diagnosis:
Allow users to select multiple symptoms instead of following a linear yes/no path.

Specialist Recommendation:
Provide recommendations for consulting specific specialists like a dermatologist, cardiologist, or ENT.

Symptom Tracking:
Implement a symptom-tracking feature where users can monitor symptoms over time and get evolving recommendations.

Integration with Medical Databases:
Connect with public health databases or APIs to provide real-time medical advice based on geographic or seasonal conditions.
