class GroupsController < ApplicationController
  filter_access_to :all

  def index
    @groups = Group.all(:include => :members)
  end

  def new
    @group = Group.new
  end

  def create
    @group = Group.new(params[:group])
    if @group.save
      flash[:notice] = 'Group created successfully'
      redirect_to groups_path
    else
      flash[:alert] = 'Group creation failed'
      render 'new'
    end
  end

  def show
    @group = Group.find(params[:id], :include => :members)
    prepare_for_show
  end

  def add_membership
    @group   = Group.find(params[:group_id])
    @members = Profile.find(params[:member_ids])
    @members.each { |member| member.groups << @group unless member.groups.include?(@group) }
    flash[:notice] = 'Members added to group successfully'
    redirect_to @group
  end

  def remove_membership
    @group   = Group.find(params[:group_id])
    @members = Profile.find(params[:member_ids])
    @members.each { |member| member.groups.delete(@group) }
    flash[:notice] = 'Members removed from group successfully'
    redirect_to @group
  end

  def invite
    @group  = Group.find(params[:group_id])
    @invite = Invite.new(params[:invite].merge(:group_id => params[:group_id]))
    if @invite.save
      Invitor.invite(:from       => current_user.email,
                     :to         => @invite.email,
                     :group_name => @group.name,
                     :link       => invite_url(@invite.token)).deliver
      flash[:notice] = "Invite sent to #{@invite.email} to join #{@invite.group.name}"
      redirect_to @group
    else
      prepare_for_show
      flash[:alert] = 'Invite was not sent'
      render 'show'
    end
  end

  protected
  def prepare_for_show
    @members     = @group.members
    @non_members = (User.contact.all - @group.members.collect { |member| member.user }).collect { |user| user.profiles }.flatten
    @invite      ||= Invite.new
  end
end