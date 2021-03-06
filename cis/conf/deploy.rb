#  Copyright 2011 Pascal Voitot [@mandubian][pascal.voitot.dev@gmail.com]
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at:
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

#################################################
# MANDATORY CONFIG TO SET 
set :application, "CIS"	# the name of the play application in general
set :repository,  "git@github.com:CIS-UNIBE-CH/CIS.git"	# for ex: ssh://xxx@github.com/git/xxx/yyy.git
set :scm, :git 				# Or: `accurev`, `bzr`, `cvs`, `darcs`, `git`, `mercurial`, `perforce`, `subversion` or `none`
set :branch, "master"

set :deploy_to, "~/cis"	# the directory where capistrano will setup it's env and clone your VCS and deploy current version etc...
					# read this for more info about Capistrano deploy https://github.com/mpasternacki/capistrano-documentation-support-files/raw/master/default-execution-path/Capistrano%20Execution%20Path.jpg
set :play_path, "/usr/play"		# this is the path in which play/play.bat can be found
# set :deploy_via, :remote_cache
#################################################
# You can let it like that
set :shared_path, "#{deploy_to}/shared"
set :app_pid, "#{shared_path}/pids/server.pid"
set :app_path, "#{deploy_to}/current/cis"

#################################################
# MANDATORY SERVER CONFIG TO SET 
set :domain, "130.92.151.134"
set :user, "user"

# set :use_sudo, false

role :web, "130.92.151.134"                          # Your HTTP server, Apache/etc
role :app, "130.92.151.134"                          # This may be the same as your `Web` server
role :db,  "130.92.151.134", :primary => true 	     

namespace :deploy do
  
  desc "Copy all relevant files to the shared ordner"
  task :copy_serverfiles do
    run "cp -nfs #{release_path}/serverconfigs/application.conf #{shared_path}/serverconfigs/"
    run "cp -nfs #{release_path}/serverconfigs/routes #{shared_path}/serverconfigs/"
    run "cp -nfs #{release_path}/serverconfigs/main.html #{shared_path}/serverconfigs/"
    run "cp -nfs #{release_path}/serverconfigs/Renderer.java #{shared_path}/serverconfigs/"
  end
  
  desc "Copy all relevant server files"
  task :symlink_shared do
    run "ln -nfs #{shared_path}/serverconfigs/application.conf #{release_path}/cis/conf/application.conf"
    run "ln -nfs #{shared_path}/serverconfigs/routes #{release_path}/cis/conf/routes"  
    run "ln -nfs #{shared_path}/serverconfigs/main.html #{release_path}/cis/app/views/main.html"
    run "ln -nfs #{shared_path}/serverconfigs/Renderer.java  #{release_path}/cis/app/models/Renderer.java"    
  end
end

after "deploy:update_code" do
  deploy.copy_serverfiles
  deploy.symlink_shared
end