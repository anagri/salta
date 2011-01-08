class Membership < ActiveRecord::Base
  belongs_to :member, :class_name => 'Profile'
  belongs_to :group
  validate :check_for_duplicates

  protected
  def check_for_duplicates
    errors.add(:member, 'User is already a member of group') if self.group.members.collect {|member| member.user}.include?(member.user)
  end
end