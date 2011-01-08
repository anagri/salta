class DashboardController < ApplicationController
  filter_access_to :all

  def index
    @user = current_user
    @profiles = current_user.profiles
    @groups = Group.all(:include => :members)
  end
end