class AndroidController < ApplicationController
  filter_access_to :all

  GROUP_FIELDS   = [:id, :name]
  MEMBER_FIELDS = [:id, :first_name, :last_name, :phone, :email]

  def groups
    render :json => current_user.groups.to_json(:only => GROUP_FIELDS)
  end

  def members
    group = current_user.groups.find(params[:group_id]).first
    render :json => group.members.sort {|one, other| one.first_name <=> other.first_name}.to_json(:only => MEMBER_FIELDS)
  end

  def member
    profile = Profile.find(params[:member_id])
    render :json => profile.to_json(:only => MEMBER_FIELDS) 
  end
end