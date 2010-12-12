class GroupsController < ApplicationController
  filter_access_to :all

  def index
    @groups = Group.all(:include => :contacts)
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
      flash[:alert] = 'Group creating failed'
      render 'new'
    end
  end

  def show
    @group = Group.find(params[:id], :include => :contacts)
    prepare_for_show
  end

  def add_membership
    @group    = Group.find(params[:group_id])
    @contacts = User.contact.find(params[:contact_ids])
    @contacts.each { |contact| contact.groups << @group unless contact.groups.include?(@group) }
    flash[:notice] = 'Contacts added to group successfully'
    redirect_to @group
  end

  def remove_membership
    @group    = Group.find(params[:group_id])
    @contacts = User.contact.find(params[:contact_ids])
    @contacts.each { |contact| contact.groups.delete(@group) }
    flash[:notice] = 'Contacts removed from group successfully'
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
    @contacts    = @group.contacts
    @non_members = User.contact.all - @group.contacts
    @invite      ||= Invite.new
  end
end