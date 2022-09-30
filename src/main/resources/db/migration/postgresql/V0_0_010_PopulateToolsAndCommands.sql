insert into Tool (
    description,
    docs_reference,
    name,
    url,
    version
)
values
(
 '',
 'https://docs.milaboratories.com/',
 'MiXCR',
 'https://github.com/milaboratory/mixcr/releases/download/v4.0.0/mixcr-4.0.0.zip',
 '4.0.0'
)

insert into Command (
    command_string,
    tool_id
)
values
(
    'align',
    '1'
)