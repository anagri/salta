class ApplicationController < ActionController::Base
  protect_from_forgery

  helper_method :current_user
  before_filter :set_current_user

  protected
  def set_current_user
    Authorization.current_user = current_user
  end

  def current_user_session
    return @current_user_session if defined?(@current_user_session)
    @current_user_session = UserSession.find
  end

  def current_user
    return @current_user if defined?(@current_user)
    @current_user = current_user_session && current_user_session.user
  end

  def permission_denied
    flash[:alert] = 'Please login to access the requested area'
    redirect_to new_user_session_path
  end
end
