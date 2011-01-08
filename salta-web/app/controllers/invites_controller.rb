class InvitesController < ApplicationController
  filter_access_to :all
  
  def show
    if current_user.profiles.empty?
      session[:from] = request.request_uri
      flash[:notice] = 'Please create a primary profile to accept the invite'
      return redirect_to new_user_profile_path(current_user)
    end
    @invite = Invite.find_by_token(params[:token])
    if @invite.nil?
      flash[:alert] = 'No such invitation exists'
    elsif @invite.email != current_user.email
      flash[:alert] = 'Sorry, this invite is not for you'
    elsif @invite.inactive?
      flash[:alert] = 'This invite has been used'
    elsif @invite.expired?
      flash[:alert] = 'This invite is no longer valid'
    else
      group = @invite.group

      group.members << current_user.primary_profile
      @invite.active = false
      @invite.save!
      flash[:notice] = "You have been added to #{group.name}"
      redirect_to group
    end
  end
end