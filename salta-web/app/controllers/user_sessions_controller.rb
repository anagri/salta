class UserSessionsController < ApplicationController
  filter_resource_access
  respond_to :html, :json, :only => [:create, :destroy]

  def new
    @user_session = UserSession.new
  end

  def create
    @user_session = UserSession.new(params[:user_session])
    if @user_session.save
      respond_with(@user_session) do |format|
        format.html { redirect_from_session_or_to root_path }
        format.json { render :nothing => true, :status => '201'}
      end
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