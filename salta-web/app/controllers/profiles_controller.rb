class ProfilesController < ApplicationController
  filter_access_to :all

  def new
    @user    = current_user
    @profile = current_user.profiles.build
  end

  def create
    @profile = current_user.profiles.build(params[:profile])
    if @profile.save
      flash[:notice] = 'Profile created successfully'
      redirect_from_session_or_to dashboard_path
    else
      flash[:alert] = 'Profile creation failed'
      render 'new'
    end
  end
end