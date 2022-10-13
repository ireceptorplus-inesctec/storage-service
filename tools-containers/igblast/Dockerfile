FROM debian:stable-20221004
WORKDIR /igblast
ADD https://ftp.ncbi.nih.gov/blast/executables/igblast/release/1.19.0/ncbi-igblast-1.19.0-x64-linux.tar.gz /igblast
RUN tar -xvf ncbi-igblast-1.19.0-x64-linux.tar.gz

WORKDIR /igblast/ncbi-igblast-1.19.0
ADD https://ftp.ncbi.nih.gov/blast/executables/igblast/release/database/mouse_gl_VDJ.tar ./database

RUN apt-get update \
    && apt-get dist-upgrade -y \
    && apt-get install -y --no-install-recommends git ca-certificates \
    && apt-get install -y --no-install-recommends git \
        ca-certificates \
        libxml2

ENTRYPOINT ["/igblast/ncbi-igblast-1.19.0/bin/igblastn"]