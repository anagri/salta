class UsersController < ApplicationController
  filter_resource_access

  def new
    @user = User.new
  end

  def create
    @user = User.new(params[:user])
    @user.role = Role::CONTACT
    if @user.save
      flash[:notice] = 'Registration successful'
      redirect_from_session_or_to groups_path
    else
      flash[:alert] = 'Registration failed'
      render 'new'
    end
  end
end