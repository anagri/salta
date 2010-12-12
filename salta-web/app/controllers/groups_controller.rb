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
    @group       = Group.find(params[:id])
    @contacts    = @group.contacts
    @non_members = User.contact.all - @group.contacts
  end

  def add_membership
    @group    = Group.find(params[:group_id])
    @contacts = User.contact.find(params[:contact_ids])
    @contacts.each { |contact| contact.groups << @group }
    flash[:notice] = 'Contacts added to group successfully'
    redirect_to @group
  end

  def remove_membership
    @group = Group.find(params[:group_id])
    @contacts = User.contact.find(params[:contact_ids])
    @contacts.each {|contact| contact.groups.delete(@group)}
    flash[:notice] = 'Contacts removed from group successfully'
    redirect_to @group
  end
end