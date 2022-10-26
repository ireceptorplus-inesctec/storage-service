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
    );

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
        'https://ncbi.github.io/igblast/',
        'IgBlast',
        'https://ftp.ncbi.nih.gov/blast/executables/igblast/release/1.20.0/ncbi-igblast-1.20.0-src.zip',
        '1.20.0'
    );



insert into Command (
    command_string,
    tool_id
)
values
    (
        'align',
        '1'
    );


insert into Command (
    command_string,
    tool_id
)
values
    (
        '-germline_db_V database/mouse_gl_V -germline_db_J database/mouse_gl_J -germline_db_D database/mouse_gl_D -organism mouse -query myseq -auxiliary_data optional_file/mouse_gl.aux -show_translation -outfmt 3',
        '2'
    );


