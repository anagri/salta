class ApplicationController < ActionController::Base
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
    if current_user.nil?
      session[:from] = request.request_uri
      flash[:alert] = 'Please login to access the requested area'
      redirect_to new_user_session_path
    else
      flash[:alert] = 'You do not have permission to access requested area'
      redirect_to root_path
    end
  end

  def redirect_from_session_or_to(path)
    redirect_to session[:from] || path
    session[:from] = nil
  end
end
