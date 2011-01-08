class HomeController < ApplicationController
  def index
    puts "current user '#{current_user.inspect}'"
    puts "group json #{current_user.groups.to_json}"
    render :json => current_user.groups.to_json
  end
end