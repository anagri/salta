class UserSessionsController < ApplicationController
  filter_resource_access

  def new
    @user_session = UserSession.new
  end

  def create
    @user_session = UserSession.new(params[:user_session])
    if @user_session.save
      redirect_to root_path
    else
      flash[:alert] = 'Sign in unsuccessful, please try again'
      render 'new'
    end
  end

  def destroy
    UserSession.find.destroy
    redirect_to new_user_session_path
  end
end