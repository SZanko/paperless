import yaml

# Custom loader to handle unknown tags and return the correct structure
class IgnoreUnknownTagsLoader(yaml.SafeLoader):
    def ignore_unknown(self, node):
        if isinstance(node, yaml.ScalarNode):
            return self.construct_scalar(node)
        elif isinstance(node, yaml.SequenceNode):
            return self.construct_sequence(node)
        elif isinstance(node, yaml.MappingNode):
            return self.construct_mapping(node)
        else:
            return None

# Add the custom constructor for ignoring unknown tags
IgnoreUnknownTagsLoader.add_constructor(None, IgnoreUnknownTagsLoader.ignore_unknown)

# Function to read YAML data from a file
def read_yaml_file(file_path: str):
    with open(file_path, 'r') as file:
        return yaml.load(file, Loader=IgnoreUnknownTagsLoader)

# Function to generate a markdown checklist
def generate_markdown_checklist(assignment) -> str:
    markdown_content = f"# Checklist for {assignment['assignmentName']} ({assignment['courseName']})\n\n"
    markdown_content += "## Tasks:\n\n"

    # Loop through tasks and add them to the markdown
    for task in assignment['tasks']:
        markdown_content += f"- [ ] **{task['title']}** (Max Grade: {task['maxGrade']})\n"

    return markdown_content

if __name__ == "__main__":
    # Read the YAML file from disk
    file_path = "Sprint1.yml"  # Replace with your actual file path
    assignment = read_yaml_file(file_path)

    # Generate markdown content
    markdown_content = generate_markdown_checklist(assignment)

    # Write the markdown content to a file
    with open("sprint1.md", "w") as file:
        file.write(markdown_content)

    print("Checklist markdown file generated successfully!")

