FROM alpine:3.16.2
WORKDIR /igblast
ADD https://ftp.ncbi.nih.gov/blast/executables/igblast/release/1.19.0/ncbi-igblast-1.19.0-x64-linux.tar.gz ./
WORKDIR /igblast/ncbi-igblast-1.19.0
ADD https://ftp.ncbi.nih.gov/blast/executables/igblast/release/database/mouse_gl_VDJ.tar ./database

ENTRYPOINT ["igblast"]