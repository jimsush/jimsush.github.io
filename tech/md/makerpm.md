Don't need to put your files under the default directories /usr/redhat/BUILDS   
mkdir SOURCES BUILD RPMS SPECS SRPMS  
mkdir testrpm-1.1-root  
cp testrpm-1.2.tar.gz SOURCES/  

Can't find some files, for example brp-compressed. In this case, you can customize your rpmmacro under your home directory. You can create a ~/.rpmmacros file in your $HOME directory; the format matches that of /usr/lib/rpm/macros  

rpm --showrc   


%__os_install_post	/pkg/OS/ia64/RH5.1AS_64/x86_64/usr/lib/rpm/brp-compress\  
/pkg/OS/ia64/RH5.1AS_64/x86_64/usr/lib/rpm/brp-strip\  
/pkg/OS/ia64/RH5.1AS_64/x86_64/usr/lib/rpm/brp-strip-static-archive\  
/pkg/OS/ia64/RH5.1AS_64/x86_64/usr/lib/rpm/brp-strip-comment-note\  
%{nil}  
  
%__check_files	/pkg/OS/ia64/RH5.1AS_64/x86_64/usr/lib/rpm/check-files  



spec  
%define _topdir /home/jim/testrpm  
%define name testrpm  
%define version 1.1  
%define buildroot %{_topdir}/%{name}-%{version}-root  
  
BuildRoot:      %{buildroot}  
Source:         %{name}-%{version}.tar.gz  

Version: 1.1  
  
  
...  
  


